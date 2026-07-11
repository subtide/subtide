(ns subtide.sc.machinery.ugen.common
  "Code that is common to many ugens."
  {:author "Jeff Rose & Christophe McKeon"}
  (:use [subtide.helpers lib]
        [subtide.sc.machinery.ugen special-ops]))

(defn real-ugen-name [ugen]
  (subtide-ugen-name
    (case (:name ugen)
      "UnaryOpUGen" (get REVERSE-UNARY-OPS (:special ugen))
      "BinaryOpUGen" (get REVERSE-BINARY-OPS (:special ugen))
      (:name ugen))))
