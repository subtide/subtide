(ns
    ^{:doc "empty ns to intern ugens so that the ugen machinery can populate the atom overloaded-ugens* before the with-overloaded-ugens macro is used."}
    subtide.sc.machinery.ugen.intern-ns)


(defn ugen-intern-ns
  "Returns the namespace to intern ugens into when loading up fn-gen for the first time."
  []
  'subtide.sc.machinery.ugen.intern-ns)
