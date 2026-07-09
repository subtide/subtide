(ns subtide.sc.cgens.bhob
  (:use [subtide.sc defcgen ugens]
        [subtide.helpers lib]))

(defcgen d-gauss
  ""
  [lo {:default 0 :doc ""}
   hi {:default 1 :doc ""}
   length {:default ##Inf :doc ""}]
  ""
  (:dr (internal:d-gauss:dr length hi lo)))
