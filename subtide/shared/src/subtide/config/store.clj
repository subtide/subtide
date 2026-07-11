(ns subtide.config.store
  "Library initialization and configuration."
  {:author "Jeff Rose"}
  (:require [subtide.config.file-store :as store]
            [subtide.helpers.file :as file]
            [subtide.helpers.string :as hstr]
            [subtide.helpers.system :as sys]
            [subtide.version :as version]))

(declare live-store)
(declare live-config)

(def CONFIG-DEFAULTS
  {:os (sys/get-os)
   :user-name (hstr/capitalize (sys/system-user-name))
   :server :external
   :sc-args {}})

(defn- file-store-get
  "Get the file store reference"
  ([store-atom key]
   (if (keyword? key)
     (get @store-atom key)
     (get-in @store-atom key)))
  ([store-atom key not-found]
   (if (keyword? key)
     (get @store-atom key not-found)
     (get-in @store-atom key not-found))))

(defn- file-store-set!
  "Set file store reference key to val"
  [store-atom key val]
  (swap! store-atom assoc key val))

(defn config-get
  "Get config value. Returns default if specified and the config does
  not contain key."
  ([key] (file-store-get live-config key))
  ([key not-found] (file-store-get live-config key not-found) ))

(defn config-set!
  "Set config key to val"
  [key val]
  (file-store-set! live-config key val)
  (str "set " key))

(defn store-get
  "Get config value. Returns default if specified and the config does
  not contain key."
  ([key] (file-store-get live-store key))
  ([key not-found] (file-store-get live-store key not-found) ))

(defn store-set!
  "Set store key to val"
  [key val]
  (file-store-set! live-store key val)
  (str "set " key))

(defn config
  "Get the full config map"
  []
  @live-config)

(defn store
  "Get the full user store map"
  []
  @live-store)

(def SUBTIDE-DIRS
  (let [root   (file/mk-path (System/getProperty "user.home") ".subtide")
        log    (file/mk-path root "log")
        assets (file/mk-path root "assets")
        speech (file/mk-path root "speech")]
      {:root root
       :log log
       :assets assets
       :speech speech}))

(def ^String SUBTIDE-CONFIG-FILE     (file/mk-path (:root   SUBTIDE-DIRS) "config.clj"))
(def ^String SUBTIDE-USER-STORE-FILE (file/mk-path (:root   SUBTIDE-DIRS) "user-store.clj"))
(def ^String SUBTIDE-ASSETS-FILE     (file/mk-path (:assets SUBTIDE-DIRS) "assets.clj"))
(def ^String SUBTIDE-LOG-FILE        (file/mk-path (:log    SUBTIDE-DIRS) "subtide.log"))

(defn- ensure-dir-structure
  []
  (run! file/mkdir! (vals SUBTIDE-DIRS)))

(defn- ensure-file
  "Creates an empty config file if one doesn't already exist"
  [path]
  (when-not (file/file-exists? path)
    (store/write-file-store path {})))

(defn- load-config-defaults
  []
  (swap! live-config (fn [config] (merge CONFIG-DEFAULTS config))))

(defn- update-seen-versions
  []
  (swap! live-config
         (fn [c]
           (let [val (get c :versions-seen #{})]
             (assoc c :versions-seen (conj val version/SUBTIDE-VERSION-STR))))))

(defonce __ENSURE-DIRS___
  (ensure-dir-structure))

(defonce __ENSURE-STORAGE-FILES__
  (do
    (ensure-file SUBTIDE-CONFIG-FILE)
    (ensure-file SUBTIDE-USER-STORE-FILE)))

(defonce live-config (store/live-file-store SUBTIDE-CONFIG-FILE))
(defonce live-store (store/live-file-store SUBTIDE-USER-STORE-FILE))

(defonce __LOAD-CONFIG__
  (try
    (load-config-defaults)
    (update-seen-versions)
    (catch Exception e
      (throw (Exception. (str "Unable to load config file - it doesn't appear to be valid clojure. Perhaps it has been modified externally? You may reset it by deleting " SUBTIDE-CONFIG-FILE " and restarting Subtide. Error: " (.printStackTrace e)))))))
