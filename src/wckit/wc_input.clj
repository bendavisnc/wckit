(ns wckit.wc-input
  )

(defn- get-input-method [wck, & args]
  (cond
    (=
      (map #(.getClass %) args)
      [java.io.File])
    :file
    :else
      (throw (Exception. "Invalid input method."))))


(defmulti input get-input-method)

(defmethod input :file [wck & [f]]
  (assoc
    wck
    :input-data
    {
      :input-source
      f
    }))
  
