(ns subtide.repl.inst
  (:require [subtide.studio.inst :refer [instrument?]]))

(defn ns-instruments
  "Returns a sequence of all the (public) instruments in the given
  namespace.

  For example:

    => (use 'subtide.inst.drum)
    nil
    => (ns-instruments 'subtide.inst.drum)
    (...)
    => (step-sequencer (metronome 200) 8 (ns-instruments 'subtide.inst.drum))

  Note that the returned sequence is a sequence of instrument
  *objects*, not vars, so they'll print sort of like this:

    #<Object$Associative$IFn$IMeta$IObj$17272e52 instrument:dub-kick>

  "
  [ns]
  (for [[n v] (ns-publics ns) :when (instrument? @v)]
    @v))
