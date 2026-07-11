(ns subtide.sc.foundation-groups
  "Foundation Group Structure"
  {:author "Sam Aaron"}
  (:require
   [subtide.libs.deps                 :refer [on-deps satisfy-deps]]
   [subtide.libs.event                :refer [on-sync-event]]
   [subtide.sc.node                   :refer [group group-deep-clear group-clear]]
   [subtide.sc.server                 :refer [ensure-connected!]]
   [subtide.sc.defaults               :refer [foundation-groups* empty-foundation-groups]]
   [subtide.sc.server                 :refer [clear-msg-queue]]
   [subtide.sc.machinery.server.comms :refer [with-server-sync]]))

(defn- setup-foundation-groups
  []
  (let [subtide-group
        (with-server-sync
          #(group "Subtide" :head 0)
          "whilst creating the main Subtide group")

        timing-group
        (with-server-sync
          #(group "Subtide Timing" :head subtide-group)
          "whilst creating the Subtide Timing group")

        input-group
        (with-server-sync
          #(group "Subtide Inputs" :after timing-group)
          "whilst creating the Subtide Inputs group")

        root-group
        (with-server-sync
          #(group "Subtide Root" :after input-group)
          "whilst creating the Subtide Root group")

        user-group
        (with-server-sync
          #(group "Subtide User" :head root-group)
          "whilst creating the Subtide User group")

        safe-pre-default-group
        (with-server-sync
          #(group "Subtide Safe Pre Default" :head user-group)
          "whilst creating the Subtide Safe Pre Default group")

        default-group
        (with-server-sync #(group "Subtide Default" :after safe-pre-default-group)
          "whilst creating the Subtide Default group")

        safe-post-default-group
        (with-server-sync
          #(group "Subtide Safe Post Default" :after default-group)
          "whilst creating the Subtide Safe Post Default group")

        output-group
        (with-server-sync #(group "Subtide Output" :after root-group)
          "whilst creating the Subtide Output group")

        monitor-group
        (with-server-sync #(group "Subtide Monitor" :after output-group)
          "whilst creating the Subtide Monitor group")]
    (swap! foundation-groups* assoc
           :subtide-group          subtide-group
           :timing-group            timing-group
           :input-group             input-group
           :root-group              root-group
           :user-group              user-group
           :safe-pre-default-group  safe-pre-default-group
           :default-group           default-group
           :safe-post-default-group safe-post-default-group
           :output-group            output-group
           :monitor-group           monitor-group)
    (satisfy-deps :foundation-groups-created)))

(on-deps :server-connected ::setup-foundation-groups setup-foundation-groups)

(defn foundation-subtide-group
  "Returns the node id for the container group for the whole of the Subtide
   foundational infrastructure. All of Subtide's groups and nodes will
   be a child of this node.

   This group should not typically be used. Prefer a group within
   foundation-user-group such as foundation-default-group or
   foundation-safe-group."
  []
  (ensure-connected!)
  (:subtide-group @foundation-groups*))

(defn foundation-timing-group
  "Returns the node id for the Subtide timing group for the default
   timer synths.

   This group should not typically be used. Prefer a group within
   foundation-user-group such as foundation-default-group or
   foundation-safe-group."
  []
  (ensure-connected!)
  (:timing-group @foundation-groups*))

(defn foundation-output-group
  "Returns the node id for the Subtide output group used for the
   default output mixers.

   This group should not typically be used. Prefer a group within
   foundation-user-group such as foundation-default-group or
   foundation-safe-group."
  []
  (ensure-connected!)
  (:output-group @foundation-groups*))

(defn foundation-monitor-group
  "Returns the node id for the Subtide output group for the default
   monitors i.e. the recording synths.

   This group should not typically be used. Prefer a group within
   foundation-user-group such as foundation-default-group or
   foundation-safe-group."
  []
  (ensure-connected!)
  (:monitor-group @foundation-groups*))

(defn foundation-input-group
  "Returns the node id for the Subtide output group for the default
   input mixers.

   This group should not typically be used. Prefer a group within
   foundation-user-group such as foundation-default-group or
   foundation-safe-group."
  []
  (ensure-connected!)
  (:input-group @foundation-groups*))

(defn foundation-root-group
  "Returns the node id for the main Subtide group for synth activity.

   This group should not typically be used. Prefer a group within
   foundation-user-group such as foundation-default-group or
   foundation-safe-group."
  []
  (ensure-connected!)
  (:root-group @foundation-groups*))

(defn foundation-user-group
  "Returns the node id for the main Subtide user group. This is where
   you should place your activity. This group already contains three
   convenience groups which you should prefer to using this group
   directly:

  * foundation-safe-pre-default-group
  * foundation-default-group
  * foundation-safe-post-default-group

  See the docstrings for these groups for more details."
  []
  (ensure-connected!)
  (:user-group @foundation-groups*))

(defn foundation-default-group
  "Returns the node id for the default Subtide group. This is where the
   majority of user activity should take place. This group is the target
   of a deep clear when the stop fn is called."
  []
  (ensure-connected!)
  (:default-group @foundation-groups*))

(defn foundation-safe-group
  "Synonym for foundation-safe-post-default-group.

  Returns the node id for a safe Subtide group. This is similar to
  the default group only it isn't the target of deep clear when the stop
  fn is called. Therefore synths in this group will *not* be
  automatically stopped on execution of the stop fn.

  This returns the safe group which is positioned *after* the default
  group. For a safe group that is positioned before the default group
  see foundation-safe-pre-default-group."
  []
  (ensure-connected!)
  (:safe-post-default-group @foundation-groups*))

(defn foundation-safe-post-default-group
  "Returns the node id for a safe Subtide group. This is similar to
  the default group only it isn't the target of deep clear when the stop
  fn is called. Therefore synths in this group will *not* be
  automatically stopped on execution of the stop fn.

  This returns the safe group which is positioned *after* the default
  group. For a safe group that is positioned before the default group
  see foundation-safe-pre-default-group."
  []
  (ensure-connected!)
  (:safe-post-default-group @foundation-groups*))

(defn foundation-safe-pre-default-group
  "Returns the node id for a safe Subtide group. This is similar to
  the default group only it isn't the target of deep clear when the stop
  fn is called. Therefore synths in this group will *not* be
  automatically stopped on execution of the stop fn.

  This returns the safe group which is positioned *after* the default
  group. For a safe group that is positioned after the default group
  see foundation-safe-post-default-group."
  []
  (ensure-connected!)
  (:safe-pre-default-group @foundation-groups*))

(on-sync-event :reset
               (fn [event-info]
                 (ensure-connected!)
                 (clear-msg-queue)
                 (group-deep-clear (foundation-default-group)))
               ::deep-clear-foundation-default-group)

(on-sync-event :reset-safe
               (fn [event-info]
                 (ensure-connected!)
                 (clear-msg-queue)
                 (group-deep-clear (foundation-safe-pre-default-group))
                 (group-deep-clear (foundation-safe-post-default-group)))
               ::deep-clear-foundation-safe-groups)

(on-sync-event :clear
               (fn [event-info]
                 (ensure-connected!)
                 (clear-msg-queue)
                 (group-clear (foundation-default-group)))
               ::clear-foundation-default-group)

(on-sync-event :clear-safe
               (fn [event-info]
                 (ensure-connected!)
                 (clear-msg-queue)
                 (group-clear (foundation-safe-pre-default-group))
                 (group-clear (foundation-safe-post-default-group)))
               ::clear-foundation-safe-groups)

(on-sync-event :shutdown
               (fn [event-info]
                 (reset! foundation-groups* empty-foundation-groups))
                ::reset-foundation-groups)
