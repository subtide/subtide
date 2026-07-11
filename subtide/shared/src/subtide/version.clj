(ns subtide.version)

(def SUBTIDE-VERSION {:major 0, :minor 16, :patch 3331, :snapshot false})

(def SUBTIDE-VERSION-STR
  (let [version SUBTIDE-VERSION]
    (str "v"
         (:major version)
         "."
         (:minor version)
         (if-not (= 0 (:patch version)) (str "." (:patch version)) "")
         (if (:snapshot version) "-dev" ""))))
