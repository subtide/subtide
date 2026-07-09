(ns user
  (:require [clojure.repl :as repl])
  (:use [subtide.core]))

(defmacro doc [& args] `(odoc ~@args))
(defn boot [] (boot-server))
