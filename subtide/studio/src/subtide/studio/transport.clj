(ns subtide.studio.transport
  (:require [subtide.music.rhythm :as rhythm]))

(def DEFAULT-BPM 128)

(def ^:dynamic *clock* (rhythm/metronome DEFAULT-BPM))
