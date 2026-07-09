(ns subtide.live
  (:require [subtide.api]))

(subtide.api/immigrate-subtide-api)

(if *compile-files*
  (println "--> (use 'subtide.live :reload) or restart JVM to use SuperCollider after compilation")
  (defonce __AUTO-BOOT__
    (when (subtide.sc.server/server-disconnected?)
      (subtide.studio.mixer/boot-server-and-mixer))))
