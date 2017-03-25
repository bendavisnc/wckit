(ns wckit.core
  (:import
    [com.kennycason.kumo CollisionMode]
    [wckit.java.core IWCKit]
    )

  (:require
    [clojure.java.io :as io]
    [common.helpers :refer [init-kumo-logging!]]
    [clojure.edn :as edn]
    )
  )

(defrecord WCKitProto [
  ]
  IWCKit
    )

(def ^:private defaults
  {
  })


;;
;;
;; Exposed helper methods...

(defn create-new []
  (map->WCKitProto defaults))

(defn createNew []
  (create-new))


(init-kumo-logging!)



