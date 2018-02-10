(ns manga-scraper.core
  (:use [net.cgrand.enlive-html :only [html-snippet select]]
        [org.httpkit.client :only [get]])
  (:import java.net.URL))

(def src "http://example-manga-site.com")

(defn get-dom
  []
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

(defn -main
  [])