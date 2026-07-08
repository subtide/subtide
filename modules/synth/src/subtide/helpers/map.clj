(ns
    ^{:doc "Helper functions for manipulating maps"
      :author "Sam Aaron"}
  subtide.helpers.map)

(defn reverse-get
  "Returns the key of the first val in maps vals that equals
  v. Non-deterministic if (vals m) contains duplicates and map isn't
  sorted."
  [m v]
  (let [f (first m)
        n (next m)]
    (if (= (val f) v)
      (key f)
      (when n (reverse-get n v)))))
