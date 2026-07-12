(ns subtide.algo.chance-test
  (:require [clojure.test :refer [deftest is]]
            [subtide.algo.chance :as sut]))

(deftest sputter-likelihood-is-controllable
  (is (= (sut/sputter [1 2] 1) (take 100 (cycle [1]))))
  (is (= (sut/sputter [1 2] 0) [1 2])))

(deftest sputter-respects-maximum-size
  (is (= (sut/sputter [1 2] 1 0) []))
  (is (= (sut/sputter [1 2] 1 5) [1 1 1 1 1])))
