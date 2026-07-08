(ns subtide.core
  (:refer-clojure :exclude [abs])
  (:require [subtide.api]))

(subtide.api/immigrate-subtide-api)

(defonce __PRINT-CONNECT-HELP__
  (when-not (server-connected?)
    (println "--> Please boot a server to start making noise:
    * (boot-server)    ; boot a SuperCollider server (scsynth)
    * (connect-server) ; connect to an existing external server
")))
