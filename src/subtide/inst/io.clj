(ns subtide.inst.io
  (:use [subtide.core]))

(definst mic [amp 1]
  (let [src (in (num-output-buses:ir))]
    (* amp src)))
