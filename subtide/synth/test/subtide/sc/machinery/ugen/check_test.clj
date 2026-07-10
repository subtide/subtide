(ns subtide.sc.machinery.ugen.check-test
  (:require [clojure.test :refer [deftest is]]
            [subtide.sc.machinery.ugen.check :as sut]))

(deftest defcheck-test
  (is (= "Argument with key :key must be a demand rate ugen"
         ((sut/arg-is-demand-ugen? :key)
          13  ; rate
          2   ; num-outs
          1   ; inputs
          {}  ; ugen
          nil ; spec
          ))))
