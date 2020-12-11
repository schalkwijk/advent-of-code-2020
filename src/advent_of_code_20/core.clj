(ns advent-of-code-20.core
  (:gen-class)
  (:require [clojure.string :as str]))

(defn to-int [string]
  (Integer/parseInt string))

(defn- solve [problem part input-file process-input]
  (let [ns-symbol (symbol (format "advent-of-code-20.problem%s.solution" problem))]
    (require ns-symbol)
    (let [target-function (ns-resolve (find-ns ns-symbol) (symbol (format "part%s" part)))]
      (target-function (process-input (slurp input-file))))))

(defn -main [& args]
  (let [problem (first args)
        part (second args)
        input-file (format "inputs/problem%s-part%s.txt" problem part)
        process-input (case problem
                        "1" #(str/split % #"\s")
                        "3" (fn [input] (map #(str/split % #"") (str/split input #"\n")))
                        "4" (fn [input] (map #(str/replace % "\n" " ") (str/split input #"\n\n")))
                        "6" #(str/split % #"\n\n")
                        #(str/split % #"\n"))]
    (println (format "Running problem %s, part %s with input file %s" problem part input-file))
    (println (solve problem part input-file process-input))))
