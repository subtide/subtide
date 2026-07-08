(ns
  ^{:doc "Code that is common to many ugens."
     :author "Jeff Rose & Christophe McKeon"}
  subtide.sc.machinery.ugen.common
  (:use [subtide.helpers lib]
        [subtide.sc.machinery.ugen special-ops]))


(defn real-ugen-name
  [ugen]
  (subtide-ugen-name
    (case (:name ugen)
      "UnaryOpUGen"
      (get REVERSE-UNARY-OPS (:special ugen))

      "BinaryOpUGen"
      (get REVERSE-BINARY-OPS (:special ugen))

      (:name ugen))))
