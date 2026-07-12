(ns subtide.helpers.lib-test
  (:require [clojure.test :refer [deftest is]]
            [subtide.helpers.lib :as sut]))

(deftest arg-mapper-test
  (let [defaults {:a 1 :b 2 :c 3 :d 4}
        names (sort (keys defaults))]
    (is (= defaults (sut/arg-mapper []
                                    names
                                    defaults)))

    (is (= (conj defaults {:a 10 :b 20})
           (sut/arg-mapper [10 20]
                           names
                           defaults)))

    (is (= (conj defaults {:a 10 :b 20 :c 30 :d 40})
           (sut/arg-mapper [10 20 :c 30 :d 40]
                           names
                           defaults)))))

(deftest double-infinity-test
  (is (= Float/POSITIVE_INFINITY (sut/to-float ##Inf)))
  (is (instance? Float (sut/to-float ##Inf)))
  (is (= Float/NEGATIVE_INFINITY (sut/to-float ##-Inf)))
  (is (instance? Float (sut/to-float ##-Inf))))
