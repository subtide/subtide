(ns subtide.helpers.hash)

(defn md5
  "Generate a md5 checksum for the given string"
  [token]
  (let [hash-bytes
        (doto (java.security.MessageDigest/getInstance "MD5")
          (.reset)
          (.update (.getBytes ^String token)))]
    (.toString
     (java.math.BigInteger. 1 (.digest hash-bytes))
     16)))
