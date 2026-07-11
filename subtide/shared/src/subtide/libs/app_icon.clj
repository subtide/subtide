(ns subtide.libs.app-icon
  (:require [clojure.java.io :as io]
            [subtide.helpers.lib :as lib]
            [subtide.helpers.system :as system])
  (:import java.awt.Toolkit))

(set! *warn-on-reflection* true)

(defn- load-icon [path]
  (let [icon-url (io/resource path)]
    (-> (Toolkit/getDefaultToolkit)
        (.createImage icon-url))))

;;FIXME not AOT compilable
(defn- set-icon [icon]
  (lib/branch (system/get-os)
    :mac (try
           (import 'com.apple.eawt.Application)
           (-> (com.apple.eawt.Application/getApplication)
               (.setDockIconImage icon))
           (catch Exception e))))

(defn- setup-icon []
  (set-icon (load-icon "subtide-logo.png")))

(defonce __INIT-ICON__
  (setup-icon))
