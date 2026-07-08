(ns subtide.smoke-test
  "Simple tests which exercise subtide and make few assertions.
  Ensures successful complilation of subtide and its examples."
  (:require [subtide.config.log :as log]
            [clojure.string :as str]
            [clojure.test :as t :refer [deftest is]]
            [subtide.test-helper :refer [eval-in-temp-ns]]))

#_ ;; FIXME mixer-booted? fails randomly
(deftest demo-test
  (eval-in-temp-ns
   (require '[clojure.test :refer [is]])
   (use 'subtide.live)

   (is (and #'defsynth #'definst) "Core macros should be defined")
   (is (server-connected?) "Server should be connected")
   (is (mixer-booted?) "Mixer should be booted")

   (demo 0.1 (sin-osc))
   (Thread/sleep 100)))


;; Examples
;;
;; Create a test for each example ns under `subtide.examples.*` which
;; loads the ns and invokes the `-main` fn if it exists and logs *out*.
;;
;; TODO:
;;  * define a `-main` function for each example
;;  * make sure at-at scheduled functions are canceled or completed

(defn all-example-ns []
  (into [] (keep #(let [n (ns-name %)]
                    (when (str/starts-with? (name n) "subtide.examples")
                      n)))
        (all-ns)))

(defn example-test-fn
  [example-ns]
  (fn []
    (println "\nRunning example:" example-ns)
    (require example-ns)
    (when-let [-main (find-var (symbol (str example-ns) "-main"))]
      ((var-get -main)))))

(defn example-test-name [example-ns]
  (symbol (str (.replace (str example-ns) "." "-") "-test")))

(defn intern-example-tests []
  (doseq [example-ns (all-example-ns)]
    (let [test-name (example-test-name example-ns)
          test-fn (example-test-fn example-ns)]
      (doto (intern *ns* test-name test-fn)
        (alter-meta! assoc :test test-fn)
        (alter-meta! merge (meta example-ns))))))

(when t/*load-tests* (intern-example-tests))

(let [ns (the-ns *ns*)]
  (defn test-ns-hook
    "Calls test-var on every var interned in this namespace, with
  fixtures. The order in which the tests are run is randomized to
  expose any inter-test dependencies that may exist."
    []
    (let [once-fixture-fn (t/join-fixtures (::t/once-fixtures (meta ns)))
          each-fixture-fn (t/join-fixtures (::t/each-fixtures (meta ns)))]
      (once-fixture-fn
       (fn []
         (doseq [v (shuffle (vals (ns-interns ns)))]
           (when (:test (meta v))
             (each-fixture-fn (fn [] (t/test-var v))))))))))
