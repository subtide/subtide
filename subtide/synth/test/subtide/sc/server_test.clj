(ns subtide.sc.server-test
  (:require [clojure.test :refer [deftest is use-fixtures]]
            [subtide.sc.server :as sut]
            [subtide.test-helper :as th]))

(use-fixtures :once th/ensure-server)

(deftest double-eval-test
  (let [a (atom 0)]
    (sut/at (swap! a inc))
    (is (= 1 @a)))
  (let [a (atom 0)]
    (sut/at-offset (swap! a inc))
    (is (= 1 @a))))
