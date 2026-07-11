(ns subtide.helpers.string-test
  (:require [clojure.test :refer [deftest is]]
            [subtide.helpers.string :as sut]))

(deftest capitalize-test
  (is (= "" (sut/capitalize "")))
  (is (= "F"
         (sut/capitalize "F")
         (sut/capitalize "f")))
  (is (= "Foo"
         (sut/capitalize "Foo")
         (sut/capitalize "foo")))
  (is (= "FOO"
         (sut/capitalize "fOO")
         (sut/capitalize "FOO"))))

(deftest str->regex-test
  (is (= (pr-str #"a")
         (pr-str (sut/str->regex "a"))
         (pr-str (sut/str->regex #"a"))))
  (is (= "a" (re-matches (sut/str->regex "a") "a"))))

(deftest numeric?-test
  (is (false? (sut/numeric? "")))
  (is (true? (sut/numeric? "1")))
  (is (true? (sut/numeric? "-1")))
  (is (false? (sut/numeric? "1M")))
  ;; FIXME
  (is (true? (sut/numeric? "1.")))
  (is (true? (sut/numeric? "1.0"))))
