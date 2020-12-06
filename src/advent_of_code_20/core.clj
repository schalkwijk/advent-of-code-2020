(ns advent-of-code-20.core
  (:gen-class)
  (:require [advent-of-code-20.problem1.solution :as sol1]
            [advent-of-code-20.problem2.solution :as sol2]
            [advent-of-code-20.problem3.solution :as sol3]
            [advent-of-code-20.problem4.solution :as sol4]
            [advent-of-code-20.problem5.solution :as sol5]
            [advent-of-code-20.problem6.solution :as sol6]
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
                     (sol1/part2 (map to-int (str/split (slurp input-file) #"\s"))))
               "2" (if (= part "1")
                     (sol2/part1 (str/split (slurp input-file) #"\n"))
                     (sol2/part2 (str/split (slurp input-file) #"\n")))
               "3" (if (= part "1")
                     (sol3/part1 (map #(str/split % #"") (str/split (slurp input-file) #"\n")))
                     (sol3/part2 (map #(str/split % #"") (str/split (slurp input-file) #"\n"))))
               "4" (if (= part "1")
                     (sol4/part1 (map #(str/replace % "\n" " ") (str/split (slurp input-file) #"\n\n")))
                     (sol4/part2 (map #(str/replace % "\n" " ") (str/split (slurp input-file) #"\n\n"))))
               "5" (if (= part "1")
                     (sol5/part1 (str/split (slurp input-file) #"\n"))
                     (sol5/part2 (str/split (slurp input-file) #"\n")))
               "6" (if (= part "1")
                     (sol6/part1 (str/split (slurp input-file) #"\n\n"))
                     (sol6/part2 (str/split (slurp input-file) #"\n\n")))))))
