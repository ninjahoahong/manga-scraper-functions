(ns manga-scraper.core
  (:require 
    [org.httpkit.server :refer [run-server]]
    [compojure.core :refer :all]
    [compojure.route :as route])
  (:use [net.cgrand.enlive-html :only [html-snippet select attr= attr-has]]
    [org.httpkit.client :only [get]])
  (:import java.net.URL))

; Examples of a source:
;(def src "http://example-manga-site.com")
;(def src-manga "http://example-manga-site.com/manga-name")
;(def src-chapter "http://example-manga-site.com/manga-name/1")


(defn get-dom
  [src]
  (html-snippet
   (:body
     @(org.httpkit.client/get
       src
       {:insecure? true}))))

(defn extract-mangareadernet-search-results
  [dom]
  (select dom [:div#mangaresults]))

(defn extract-mangareadernet-search-items
  [dom]
  (select dom [:div.mangaresultitem]))

(defn extract-mangareadernet-search-item-chapter-count
  [dom]
  (select dom [:div.chapter_count]))

(defn extract-chapter-counts
  []
  (map #(select % [:div.chapter_count]) (extract-mangareadernet-search-items (get-dom))))

(defn count-number-of-chapters-in-a-manga-mangareadernet
  [src-manga]
  (count (select (select (get-dom src-manga) [:div#chapterlist]) [:tr])))

(defn count-number-of-pages-in-a-chapter-mangareadernet
  [src-chapter]
  (count (select (get-dom src-chapter) [:option])))

(defn print-all-pages-in-a-chapter-mangareadernet
  [src-chapter]
  (dotimes [n (count-number-of-pages-in-a-chapter-mangareadernet src-chapter)] (println (str src-chapter (str "/") (+ n 1)))))

(defn get-page-img-src-mangareadernet
  [src-page]
  (:src (:attrs (first (select (get-dom src-page) [:#img])))))

(defn get-all-mangas
  []
  (let [response {:status  200
                  :headers {"Content-Type" "text/html"}
                  :body    (str "Hello")}]
    response))

(defroutes allroutes
  (GET "/" [] "<h1>Welcome</h1>")
  (GET "/mangas" [] (get-all-mangas))
  (route/not-found "<h1>Page not found</h1>"))

(defn -main [& args]
  (run-server allroutes {:port 8080})
  (println "Server started on port 8080"))