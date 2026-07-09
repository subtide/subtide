(ns subtide.helpers.lib-test
  (:require [clojure.test :refer [deftest is]]
            [subtide.helpers.lib :as sut]))

(deftest double-infinity-test
  (is (= Float/POSITIVE_INFINITY (sut/to-float ##Inf)))
  (is (= Float/NEGATIVE_INFINITY (sut/to-float ##-Inf))))
