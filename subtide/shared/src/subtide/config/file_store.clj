(ns subtide.config.file-store
  "Provides a simple key/value store which will automatically persist to a file on
  disk. The file is serialized clojure code which can be easily edited as text."
  {:author "Jeff Rose, Kevin Neaton"}
  (:require [clojure.pprint :as pp]
            [subtide.helpers.file :as file-helpers]))

;; This should be temporary...
(def storage (constantly :file))

;; Store interface:
(defmulti write-file-store storage)
(defmulti read-file-store  storage)

(defonce F-LOCK (Object.))

;; Simple file-based storage
(defmethod write-file-store :file
  [path data]
  (locking F-LOCK
    (binding [*print-length* nil
              *print-level* nil]
      (spit path (with-out-str (pp/pprint data))))))

(defmethod read-file-store :file
  [path]
  (locking F-LOCK
    (when (file-helpers/path-exists? path)
      (read-string (slurp path)))))

(defmethod read-file-store :file
  [path]
  (locking F-LOCK
    (when (file-helpers/path-exists? path)
      (read-string (slurp path)))))

(defn live-file-store
  "Returns an atom representing a file-store map located at the given
   path. Persists any changes to disk as they occur within the atom.

   Creates file if doesn't exist and initialises value with empty map.

   (def data (live-file-store \"~/.app-data\"))
   (swap! data assoc :foo 1) ;=> persists key val pair to ~/.app-data"
  [path]
  (let [store-atom (atom (or (read-file-store path) {}))]
    (add-watch store-atom ::live-file-store
               (fn [k r old-state new-state]
                 (when-not (= old-state new-state)
                   (write-file-store path new-state))))
    store-atom))
