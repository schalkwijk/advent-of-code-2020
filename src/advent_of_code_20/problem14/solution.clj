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

(defn- apply-single-mask-2 [[bit mask]]
  (case mask
    \X mask
    \0 bit
    \1 mask))

(defn- parse-long [number] (Long/parseLong number 2))

(defn- mask-address [index address]
  (map #(apply str (assoc (str/split address #"") index %)) ["0" "1"]))

(defn- generate-addresses [masked-address]
  (let [index (.indexOf masked-address "X")]
    (if (= index -1)
      [masked-address]
      (mapcat generate-addresses (mask-address index masked-address)))))

(defn- fetch-addresses [address mask]
  (let [bit-address (Integer/toString address 2)
        total-bits (count bit-address)
        final-bit-address (format (str "%0" (- (count mask) total-bits) "d%s") 0 bit-address)]
    (map parse-long (generate-addresses (apply str (map apply-single-mask-2 (map vector final-bit-address mask)))))))

(defn- assign-value-2 [{:keys [mask] :as state} {:keys [address value] :as instruction}]
  (reduce #(assoc-in %1 [:memory %2] value) state (fetch-addresses address mask)))

(defn- process-instruction-2 [state {:keys [op] :as instruction}]
  (if (= op :mem) (assign-value-2 state instruction) (assign-mask state instruction)))

(defn part1 [input]
  (apply + (vals (:memory (reduce process-instruction {:memory {} :mask nil} (map parse-line input))))))

(defn part2 [input]
  (apply + (vals (:memory (reduce process-instruction-2 {:memory {} :mask nil} (map parse-line input))))))
