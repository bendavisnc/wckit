(ns common.helpers
)

(defmacro init-kumo-logging! []
  "Whatever that^ is, init it so it doesn't scream eh.. \"warn\" us."
  '(org.apache.log4j.BasicConfigurator/configure))

