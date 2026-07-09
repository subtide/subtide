(ns subtide.dev.cmdline
  (:require [nrepl.cmdline :as cmdline]))

(let [prefix (cmdline/repl-intro)]
  (defn repl-intro []
    (str prefix
         "\nRun (use 'subtonic.live) to boot subtonic."
         "\nThen:"
         "\n (demo (sin-osc)) to make a noise"
         "\n (odoc sin-osc) to read docs"
         )))


(defn -main [& args]
  (with-redefs [cmdline/repl-intro repl-intro]
    (cmdline/dispatch-commands
      {:interactive true
       :repl-intro repl-intro})))
