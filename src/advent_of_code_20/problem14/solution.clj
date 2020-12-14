(ns advent-of-code-20.problem14.solution
  (:require [clojure.string :as str]))

(defn- apply-single-mask [[bit mask]]
  (if (= \X mask) bit mask))

(defn apply-mask [mask number]
  (let [bit-number (Integer/toString number 2)
        total-bits (count bit-number)
        final-bit-number (format (str "%0" (- (count mask) total-bits) "d%s") 0 bit-number)]
    (Long/parseLong (apply str (map apply-single-mask (map vector final-bit-number mask))) 2)))

(defn- assign-value [{:keys [mask] :as state} {:keys [address value] :as instruction}]
  (assoc-in state [:memory address] (apply-mask mask value)))

(defn- assign-mask [state instruction]
  (assoc state :mask (:mask instruction)))

(defn- parse-mem [line]
  (let [[address value] (rest (re-matches #"mem\[(.*)\] = (.*)" line))]
    {:op :mem :address (Integer/parseInt address) :value (Integer/parseInt value)}))

(defn- parse-mask [line]
  (let [mask (last (re-matches #"mask = (.*)" line))]
    {:op :mask :mask mask}))

(defn- parse-line [line]
  (if (nil? (re-matches #"mask.*" line))
    (parse-mem line)
    (parse-mask line)))

(defn- process-instruction [state {:keys [op] :as instruction}]
  (if (= op :mem) (assign-value state instruction) (assign-mask state instruction)))

(defn part1 [input]
  (apply + (vals (:memory (reduce process-instruction {:memory {} :mask nil} (map parse-line input))))))

(defn part2 [input] 10)
