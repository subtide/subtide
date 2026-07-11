(ns subtide.api
  (:import [java.lang.management ManagementFactory])
  (:use
   subtide.helpers.ns
   subtide.libs.boot-msg)
  (:require
   clojure.stacktrace
   [subtide.helpers.doc :refer [fs]]
   (subtide.helpers rand)
   (subtide version osc speech)
   (subtide.algo chance scaling trig fn lists)
   (subtide.config store)
   (subtide.libs asset event)
   (subtide.music rhythm pitch tuning time)
   (subtide.repl debug examples graphviz
                  inst shell ugens)
   [subtide.freesound]
   (subtide.sc bindings buffer bus envelope example info
                ugens defcgen node sample server synth clock
                foundation-groups dyn-vars trig vbap)
   (subtide.sc.cgens oscillators demand mix dyn io buf-io env tap
                      line freq beq-suite berlach ;; bhob
                      fx info)
   (subtide.studio aux-bus mixer inst util fx wavetable midi midi-player core
                    pattern event)))


;; Currently the default lein setting drastically reduces performance in
;; return for a 200ms improvement of the startup time. See:
;; https://github.com/technomancy/leiningen/pull/1230
(defonce __PRINT_TIERED_COMPILATION_WARNING__
  (let [compiler-bean (ManagementFactory/getCompilationMXBean)
        compiler-name (.getName compiler-bean)
        runtime-bean (ManagementFactory/getRuntimeMXBean)
        input-args    (.getInputArguments runtime-bean)]

    (when-let [_arg (and (re-find #"Tiered" compiler-name)
                         (some #(re-find #"TieredStopAtLevel=1" %)
                               input-args))]
      (println
       (fs "**********************************************************
            WARNING: JVM argument TieredStopAtLevel=1 is active, and may
            lead to reduced performance. This happens to currently be
            the default lein setting:

            https://github.com/technomancy/leiningen/pull/1230

            If you didn't intend this JVM arg to be specified, you can
            turn it off in your project.clj file or your global
            ~/.lein/profiles.clj file by adding the key-val

            :jvm-opts ^:replace []
              **********************************************************")))))

(def immigrated-namespaces
  ['subtide.algo.chance
   'subtide.algo.fn
   'subtide.algo.lists
   'subtide.algo.scaling
   'subtide.algo.trig
   'subtide.config.store
   'subtide.libs.asset
   'subtide.libs.event
   'subtide.music.pitch
   'subtide.music.rhythm
   'subtide.music.time
   'subtide.osc
   'subtide.repl.debug
   'subtide.repl.examples
   'subtide.repl.graphviz
   'subtide.repl.inst
   'subtide.repl.shell
   'subtide.repl.ugens
   'subtide.freesound
   'subtide.sc.bindings
   'subtide.sc.buffer
   'subtide.sc.bus
   'subtide.sc.cgens.beq-suite
   'subtide.sc.cgens.berlach
   ;; 'subtide.sc.cgens.bhob
   'subtide.sc.cgens.buf-io
   'subtide.sc.cgens.demand
   'subtide.sc.cgens.dyn
   'subtide.sc.cgens.env
   'subtide.sc.cgens.freq
   'subtide.sc.cgens.fx
   'subtide.sc.cgens.info
   'subtide.sc.cgens.io
   'subtide.sc.cgens.line
   'subtide.sc.cgens.mix
   'subtide.sc.cgens.oscillators
   'subtide.sc.cgens.tap
   'subtide.sc.clock
   'subtide.sc.defcgen
   'subtide.sc.dyn-vars
   'subtide.sc.envelope
   'subtide.sc.example
   'subtide.sc.foundation-groups
   'subtide.sc.info
   'subtide.sc.node
   'subtide.sc.sample
   'subtide.sc.server
   'subtide.sc.synth
   'subtide.sc.trig
   'subtide.sc.ugens
   'subtide.sc.vbap
   'subtide.speech
   'subtide.studio.aux-bus
   'subtide.studio.core
   'subtide.studio.fx
   'subtide.studio.inst
   'subtide.studio.midi
   'subtide.studio.midi-player
   'subtide.studio.mixer
   'subtide.studio.wavetable
   'subtide.version
   'subtide.studio.pattern
   'subtide.studio.event
   'subtide.studio.transport
   'subtide.helpers.rand
   ])

(defn immigrate-subtide-api []
  (apply immigrate immigrated-namespaces))
