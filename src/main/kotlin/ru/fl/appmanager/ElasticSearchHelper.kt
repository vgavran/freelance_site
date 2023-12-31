package ru.fl.appmanager

import com.google.gson.JsonElement
import com.google.gson.JsonParser
import org.apache.http.HttpHost
import org.apache.http.impl.client.HttpClients
import org.apache.http.impl.client.LaxRedirectStrategy
import org.apache.http.util.EntityUtils
import org.elasticsearch.client.Request
import org.elasticsearch.client.Response
import org.elasticsearch.client.RestClient
import java.io.IOException
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class ElasticSearchHelper() {

    init {
        val httpClient = HttpClients.custom().setRedirectStrategy(LaxRedirectStrategy()).build()
    }

    //Запрос elasticsearch, возвращает всех фрилансеров в каталоге
    @Throws(IOException::class)
    fun getAllFreelInCatalog(): Int {
            //Запрос elasticsearch, возвращает всех фрилансеров в каталоге
            val fortyDaysAgo: LocalDateTime = LocalDateTime.now().minusDays(14)
            val date: String = fortyDaysAgo.toString().replace("T", " ").substring(0, 19)
            val client: RestClient = RestClient.builder(
                HttpHost("192.168.10.7", 9200, "http"),
                HttpHost("192.168.10.7", 9201, "http")
            ).build()
            val queryJson = """{
              "size": 1000,
              "query": {
                "bool" : {
                  "must": [
                    {
                      "term": {
                        "identity_verify_status": {
                          "value": 6
                        }
                      }
                    },
                    {
                      "term":{
                        "is_banned":false
                        }
                      },
                      {
                        "term":{
                        "is_active":true
                        }
                      },
                    {
                      "range": {
                        "last_time": {
                          "from": "$date"
                        }
                      }
                    }
                  ]
                }
              },
              "_source": true
            }"""
            var request = Request("GET", "/catalog/_search")
            request.setJsonEntity(queryJson)
            val response: Response = client.performRequest(request)
            val responseBody: String = EntityUtils.toString(response.entity)
            val hits: JsonElement = JsonParser.parseString(responseBody).asJsonObject["hits"]
            val total: JsonElement = hits.asJsonObject.get("total")
            return total.asJsonObject.get("value").asInt
        }

    //Запрос elasticsearch, возвращает всех фрилансеров в каталоге по заданной специализации (27 - сайт под ключ)
    @Throws(IOException::class)
    fun getFreelFilterSpecializationInCatalog() : Int{
            //Запрос elasticsearch, возвращает всех фрилансеров в каталоге по заданной специализации (27 - сайт под ключ)
            val fortyDaysAgo: LocalDateTime = LocalDateTime.now().minusDays(14)
            val date: String = fortyDaysAgo.toString().replace("T", " ").substring(0, 19)
            val client: RestClient = RestClient.builder(
                HttpHost("192.168.10.7", 9200, "http"),
                HttpHost("192.168.10.7", 9201, "http")
            ).build()
            val queryJson = """{
              "query":{
              "bool":{
                "must":{
                  "query_string":{
                    "query":"* *"}
                  
                },
                "filter":{
                  "bool":{
                    "must":[
                      {
                        "term":{
                          "identity_verify_status":6
                        }
                      },
                      {
                        "term":{
                        "is_banned":false
                        }
                      },
                      {
                        "term":{
                        "is_active":true
                          
                        }
                      },
                      {
                        "range":{
                          "last_time":{
                            "gte": "$date"
                          }
                        }
                      },
                      {
                        "bool":{
                        "should":[
                          {
                            "terms":{
                              "spec":[27]
                            }
                          },
                          {
                            "terms":{
                              "paid_specialization":[27]
                              
                            }
                            
                          },
                          {
                            "bool":{
                              "must":[
                                {
                                  "terms":{
                                    "extended_specialization":[27]
                                    
                                  }
                                },
                                {
                                  "term":{
                                    "is_pro":true
                                    
                                  }
                                }
                              ]
                            }
                          },
                          {"bool":{
                            "must":[
                              {
                                "term":{
                                  "binds.prof_id":2
                                }
                              },
                              {
                                "term":{
                                  "binds.is_spec":false
                                      }
                                    }
                                  ]
                                }
                              }
                            ]
                          }
                        }
                      ]
                    }
                  }
                }
              },
              "from":0,"size":30}"""
            var request = Request("GET", "/catalog/_search")
            request.setJsonEntity(queryJson)
            val response: Response = client.performRequest(request)
            val responseBody: String = EntityUtils.toString(response.getEntity())
            val hits: JsonElement = JsonParser.parseString(responseBody).asJsonObject["hits"]
            val total: JsonElement = hits.getAsJsonObject().get("total")
            return total.getAsJsonObject().get("value").getAsInt()
        }

    //Запрос elasticsearch, возвращает всех фрилансеров в каталоге в соответствии с заданными страной и городом (1 - Россия, 1 - Москва)
    @Throws(IOException::class)
    fun getFreelFilterCityInCatalog() : Int {
            //Запрос elasticsearch, возвращает всех фрилансеров в каталоге в соответствии с заданными страной и городом (1 - Россия, 1 - Москва)
            val fortyDaysAgo: LocalDateTime = LocalDateTime.now().minusDays(14)
            val date: String = fortyDaysAgo.toString().replace("T", " ").substring(0, 19)
            val client: RestClient = RestClient.builder(
                HttpHost("192.168.10.7", 9200, "http"),
                HttpHost("192.168.10.7", 9201, "http")
            ).build()
            val queryJson = """{
              "query":{
                "bool":{
                  "must":{
                    "query_string":{
                      "query":"* *"
                    }
                  },
                  "filter":{
                    "bool":{
                      "must":[
                        {
                          "term":{
                            "identity_verify_status":6
                          }
                        },
                        {
                          "term":{
                          "is_banned":false
                          }
                        },
                        {
                          "term":{
                            "is_active":true
                          }
                        },
                        {
                          "range":{
                            "last_time":{
                              "gte": "$date"
                            }
                          }
                        },
                        {
                          "terms":{
                            "country":[1]
                          }
                        },
                        {
                          "terms":{
                            "city":[1]
                          }
                        }
                      ]
                    }
                  }
                }
              },
              "from":0,"size":30}"""
            var request = Request("GET", "/catalog/_search")
            request.setJsonEntity(queryJson)
            val response: Response = client.performRequest(request)
            val responseBody: String = EntityUtils.toString(response.getEntity())
            val hits: JsonElement = JsonParser.parseString(responseBody).asJsonObject["hits"]
            val total: JsonElement = hits.getAsJsonObject().get("total")
            return total.getAsJsonObject().get("value").getAsInt()
        }

    //Запрос elasticsearch, возвращает всех фрилансеров в каталоге в соответствии с заданным сроком на fl.com (более 3-х лет)
    @Throws(IOException::class)
    fun getFreelFilterRegDateInCatalog(): Int {
            //Запрос elasticsearch, возвращает всех фрилансеров в каталоге в соответствии с заданным сроком на fl.com (более 3-х лет)
            val fortyDaysAgo: LocalDate = LocalDate.now().minusDays(14)
            val fourYearsAgo: LocalDate = LocalDate.now().minusYears(3)
            val time = LocalTime.now().withNano(0)
            val date = "$fortyDaysAgo $time"
            val regDate: String = fourYearsAgo.toString()
            val client: RestClient = RestClient.builder(
                HttpHost("192.168.10.7", 9200, "http"),
                HttpHost("192.168.10.7", 9201, "http")
            ).build()
            val queryJson = """{
              "query":{
                "bool":{
                  "must":{
                    "query_string":{
                      "query":"* *"
                    }
                  },
                  "filter":{
                    "bool":{
                      "must":[
                        {
                          "term":{
                            "identity_verify_status":6
                          }
                        },
                        {
                          "term":{
                          "is_banned":false
                          }
                        },
                        {
                          "term":{
                            "is_active":true
                          }
                        },
                        {
                          "range":{
                            "last_time":{
                              "gte": "$date"
                            }
                          }
                        },
                        {
                          "range":{
                            "reg_date":{
                              "lte": "$regDate"
                            }
                          }
                        }
                      ]
                    }
                  }
                }
              },
              "from":0,"size":30}"""
            var request = Request("GET", "/catalog/_search")
            request.setJsonEntity(queryJson)
            val response: Response = client.performRequest(request)
            val responseBody: String = EntityUtils.toString(response.getEntity())
            val hits: JsonElement = JsonParser.parseString(responseBody).asJsonObject["hits"]
            val total: JsonElement = hits.getAsJsonObject().get("total")
            return total.getAsJsonObject().get("value").getAsInt()
        }

    //Запрос elasticsearch, возвращает всех фрилансеров в каталоге в соответствии с заданными навыками ("git" - 1060)
    @Throws(IOException::class)
    fun getFreelFilterSkillsInCatalog(): Int {
            //Запрос elasticsearch, возвращает всех фрилансеров в каталоге в соответствии с заданными навыками ("git" - 1060)
            val fortyDaysAgo: LocalDateTime = LocalDateTime.now().minusDays(14)
            val date: String = fortyDaysAgo.toString().replace("T", " ").substring(0, 19)
            val client: RestClient = RestClient.builder(
                HttpHost("192.168.10.7", 9200, "http"),
                HttpHost("192.168.10.7", 9201, "http")
            ).build()
            val queryJson = """{
              "query":{
                "bool":{
                  "must":{
                    "query_string":{
                      "query":"* *"
                    }
                  },
                  "filter":{
                    "bool":{
                      "must":[
                        {
                          "term":{
                            "identity_verify_status":6
                          }
                        },
                        {
                          "term":{
                          "is_banned":false
                          }
                        },
                        {
                          "term":{
                            "is_active":true
                          }
                        },
                        {
                          "term":{
                            "skills":1060 
                          }
                        },
                        {
                          "range":{
                            "last_time":{
                              "gte": "$date"
                            }
                          }
                        }
                      ]
                    }
                  }
                }
              },
              "from":0,"size":30}"""
            var request = Request("GET", "/catalog/_search")
            request.setJsonEntity(queryJson)
            val response: Response = client.performRequest(request)
            val responseBody: String = EntityUtils.toString(response.getEntity())
            val hits: JsonElement = JsonParser.parseString(responseBody).asJsonObject["hits"]
            val total: JsonElement = hits.getAsJsonObject().get("total")
            return total.getAsJsonObject().get("value").getAsInt()
        }

    //Запрос elasticsearch, возвращает всех фрилансеров в каталоге с примерами работ
    @Throws(IOException::class)
    fun getFreelFilterWithPortfolioInCatalog(): Int {
            //Запрос elasticsearch, возвращает всех фрилансеров в каталоге с примерами работ
            val fortyDaysAgo: LocalDateTime = LocalDateTime.now().minusDays(14)
            val date: String = fortyDaysAgo.toString().replace("T", " ").substring(0, 19)
            val client: RestClient = RestClient.builder(
                HttpHost("192.168.10.7", 9200, "http"),
                HttpHost("192.168.10.7", 9201, "http")
            ).build()
            val queryJson = """{
              "query":{
                "bool":{
                  "must":{
                    "query_string":{
                      "query":"* *"
                    }
                  },
                  "filter":{
                    "bool":{
                      "must":[
                        {
                          "term":{
                            "identity_verify_status":6
                          }
                        },
                        {
                          "term":{
                          "is_banned":false
                          }
                        },
                        {
                          "term":{
                            "is_active":true
                          }
                        },
                        {
                          "term":{
                            "with_portfolio":true
                          }
                        },
                        {
                          "range":{
                            "last_time":{
                              "gte": "$date"
                            }
                          }
                        }
                      ]
                    }
                  }
                }
              },
              "from":0,"size":30}"""
            var request = Request("GET", "/catalog/_search")
            request.setJsonEntity(queryJson)
            val response: Response = client.performRequest(request)
            val responseBody: String = EntityUtils.toString(response.getEntity())
            val hits: JsonElement = JsonParser.parseString(responseBody).asJsonObject["hits"]
            val total: JsonElement = hits.getAsJsonObject().get("total")
            return total.getAsJsonObject().get("value").getAsInt()
        }

    //Запрос elasticsearch, возвращает всех фрилансеров в каталоге с отзывами
    @Throws(IOException::class)
    fun getFreelFilterWithFeedbacksInCatalog(): Int {
            //Запрос elasticsearch, возвращает всех фрилансеров в каталоге с отзывами
            val fortyDaysAgo: LocalDateTime = LocalDateTime.now().minusDays(14)
            val date: String = fortyDaysAgo.toString().replace("T", " ").substring(0, 19)
            val client: RestClient = RestClient.builder(
                HttpHost("192.168.10.7", 9200, "http"),
                HttpHost("192.168.10.7", 9201, "http")
            ).build()
            val queryJson = """{
              "query":{
                "bool":{
                  "must":{
                    "query_string":{
                      "query":"* *"
                    }
                  },
                  "filter":{
                    "bool":{
                      "must":[
                        {
                          "term":{
                            "identity_verify_status":6
                          }
                        },
                        {
                          "term":{
                          "is_banned":false
                          }
                        },
                        {
                          "term":{
                            "is_active":true
                          }
                        },
                        {
                          "term":{
                            "with_opinions":true
                          }
                        },
                        {
                          "range":{
                            "last_time":{
                              "gte": "$date"
                            }
                          }
                        }
                      ]
                    }
                  }
                }
              },
              "from":0,"size":30}"""
            var request = Request("GET", "/catalog/_search")
            request.setJsonEntity(queryJson)
            val response: Response = client.performRequest(request)
            val responseBody: String = EntityUtils.toString(response.getEntity())
            val hits: JsonElement = JsonParser.parseString(responseBody).asJsonObject["hits"]
            val total: JsonElement = hits.getAsJsonObject().get("total")
            return total.getAsJsonObject().get("value").getAsInt()
        }
}