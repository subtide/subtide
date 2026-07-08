(ns subtide.sc.cgens.berlach
  (:use [subtide.sc defcgen ugens]
        [subtide.helpers seq]))

(defcgen soft-clip-amp
  "Berlach Soft Clip Amp"
  [in {:doc "Input signal"}
   pregain {:default 1 :doc ""}]
  ""
  (:ar (softclip (* in pregain))))
