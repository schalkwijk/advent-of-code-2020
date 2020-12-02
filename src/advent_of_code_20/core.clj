(ns advent-of-code-20.core
  (:gen-class)
  (:require [advent-of-code-20.problem1.solution1 :as sol1]
            [clojure.string :as str]))

(defn to-int [string]
  (Integer/parseInt string))

(defn -main [& args]
  (let [problem (first args)
        part (second args)
        input-file (last args)]
    (println (format "Running problem %s, part %s with input file %s" problem part input-file))
    (println (case problem
               "1" (if (= part "1")
                   (sol1/part1 (map to-int (str/split (slurp input-file) #"\s")))
                   (sol1/part2 (map to-int (str/split (slurp input-file) #"\s"))))))))
