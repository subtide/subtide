(ns subtide.algo.euclidean-rhythm-test
  (:require [clojure.test :refer [deftest is]]
            [subtide.algo.euclidean-rhythm :as sut]))

(deftest euclidean-rhythm-generates-correct-patterns
  (is (= (sut/euclidean-rhythm 1 2) [1 0]))
  (is (= (sut/euclidean-rhythm 1 3) [1 0 0]))
  (is (= (sut/euclidean-rhythm 2 5) [1 0 1 0 0]))
  (is (= (sut/euclidean-rhythm 3 8) [1 0 0 1 0 0 1 0])))

(deftest euclidean-rhythm-throws-if-pulses-exceeds-steps
  (is (thrown? Exception (sut/euclidean-rhythm 100 1))))
