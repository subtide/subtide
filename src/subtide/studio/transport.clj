(ns subtide.studio.transport
  (:use [subtide.music rhythm]))

(def DEFAULT-BPM 128)

(def ^:dynamic *clock* (metronome DEFAULT-BPM))
