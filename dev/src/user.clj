(ns user
  (:require [clojure.repl :as repl])
  (:use [subtide.live]))

(defmacro doc [& args] `(odoc ~@args))
