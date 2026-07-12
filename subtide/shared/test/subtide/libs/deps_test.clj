(ns subtide.libs.deps-test
  (:require [clojure.test :refer [deftest is testing]]
            [subtide.libs.deps :as sut]))

(deftest deps-basic-test
  (with-redefs [sut/dep-state* (agent {:satisfied #{}
                                       :todo      []
                                       :done      []
                                       :history   []})]
    (let [log (atom [])]
      (sut/on-deps :foo ::swapper
                   #(swap! log conj :a))
      (sut/satisfy-deps :foo)
      (sut/on-deps :foo ::another-swapper
                   #(swap! log conj :b))
      (sut/on-deps [:foo :bar] ::third-swapper
                   #(swap! log conj :c))
      (sut/on-deps #{:foo :bar :baz} ::fourth-swapper
                   #(swap! log conj :d))
      (sut/satisfy-deps :bar)
      (sut/wait-until-deps-satisfied :bar)
      (sut/satisfy-deps :baz)
      (sut/wait-until-deps-satisfied :baz)
      (sut/on-deps #{:foo :baz} ::fifth-swapper
                   #(do (swap! log conj :e)
                        (sut/satisfy-deps :done)))
      (sut/wait-until-deps-satisfied :done)
      (is (= [:a :b :c :d :e] @log)))))

(deftest deps-start-via-empty-on-deps-test
  (dotimes [_ 10]
    (with-redefs [sut/dep-state* (agent {:satisfied #{}
                                         :todo      []
                                         :done      []
                                         :history   []})]
      (let [log (atom [])
            fs [#(sut/on-deps #{} ::start
                              (fn []
                                (swap! log conj :init)
                                (sut/satisfy-deps :foo)))
                #(sut/on-deps :foo ::a
                              (fn []
                                (swap! log conj :a)
                                (sut/satisfy-deps :second)))
                #(sut/on-deps :second ::b
                              (fn []
                                (swap! log conj :b)
                                (sut/satisfy-deps :bar)))
                #(sut/on-deps [:foo :bar] ::c
                              (fn []
                                (swap! log conj :c)
                                (sut/satisfy-deps :baz)))
                #(sut/on-deps #{:foo :bar :baz} ::d
                              (fn []
                                (swap! log conj :d)))
                #(sut/on-deps #{:foo :baz} ::e
                              (fn []
                                (swap! log conj :e)
                                (sut/satisfy-deps :done)))]
            order (shuffle (vec (range (count fs))))]
        (testing (pr-str order)
          (run! #(future ((nth fs %))) order)
          (is (true? (try (sut/wait-until-deps-satisfied :done)
                          true
                          (catch Exception e e))))
          (is (contains? #{[:init :a :b :c :d :e]
                           [:init :a :b :c :e :d]} @log))
          (is (contains? #{[::start ::a ::b ::c ::d ::e]
                           [::start ::a ::b ::c ::e ::d]}
                         (keep #(when (#{:processed :satisfied-and-processed} (:action %))
                                  (:key %))
                               (:history @sut/dep-state*)))))))))
