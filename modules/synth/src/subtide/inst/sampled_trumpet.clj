(ns subtide.inst.sampled-trumpet
  "A trumpet instrument based on a sample pack.

  Originally posted at https://github.com/karlthorssen/subtide-trumpet,
  included here with permission."
  {:author "Karl Thorssen"}
  (:require
   [subtide.core :as subtide]
   [subtide.samples.trumpet :refer [trumpet-index-buffer]]))

(subtide/definst sampled-trumpet
  "Trumpet based on samples from freesound.org

   (sampled-trumpet) ;; plays middle c on trumpet
   (sampled-trumpet :pitch 63 :attack 0.05 :level 1.5 :start-pos 0.1) ;; plays a different note

   To control the duration of a note, use the :gate argument. For example,

   (defn play-gated [duration-sec node]
     (let [duration-ms (* 1000 duration-sec)]
       (at (+ (now) duration-ms)
           (ctl node :gate 0))))

   ;; An A minor chord for a quarter note at 130 bpm
   (doseq [midi-note [57 60 64]]
     (play-gated 0.46 (sampled-trumpet :pitch midi-note)))"
  [note 60 level 1 rate 1 loop? 0
   attack 0 decay 1 sustain 1 release 0.1 curve -4 gate 1 start-pos 0.0]
  (let [buf (subtide/index:kr (:id trumpet-index-buffer) note)
        rate (:rate trumpet-index-buffer)
        env (subtide/env-gen (subtide/adsr attack decay sustain release level curve)
                              :gate gate
                              :action subtide/FREE)]
    (subtide/pan2 :in (* env (subtide/scaled-play-buf 1 buf :level level :loop loop?
                                                        :start-pos (* rate start-pos)
                                                        :action subtide/FREE))
                   :pos 0)))
