import { request } from "./request";

export function deleteDoc(userId, articleId) {
  return request({
    method: 'get',
    url: '/article/deleteCart',
    params: {
      user_id: userId,
	  article_id: articleId
    }
  })
}

export function getCart(userId) {
  return request({
    method: 'get',
    url: '/article/getCart',
    params: {
      id: userId,
    }
  })
}

export function dealCart(purchasedList) {
  return request({
      method: 'post',
      url: '/article/dealCart',
      data: {
          purchasedList: purchasedList,
      }
  })
}

export function uploadArticleByForm(form) {
    return request({
        method: 'post',
        url: '/article/uploadArticle',
        data: form,
    })
}


export function getArticle(pageSize, pageNum, keyWord, type) {
  return request({
      method: 'post',
      url: '/article/getArticleByScreen',
      data: {
          pageSize: pageSize,
		  pageNum: pageNum,
		  keyWord: keyWord,
		  type: type,
      }
  })
}

export function getArticleDetail(article_id, user_id) {
    return request({
        method: 'get',
        url: '/article/getArticleDetail',
        params: {
            article_id: article_id,
            user_id: user_id,
        }
    })
}

export function getRecommendArticle(id) {
    return request({
        method: 'get',
        url: '/article/getRecommendArticle',
        params: {
            id: id,
        }
    })
}

export function getPopularArticle() {
    return request({
        method: 'get',
        url: '/article/getPopularArticle',
        params: {}
    })
}


export function addCart(article_id, user_id) {
    return request({
        method: 'get',
        url: '/article/addCart',
        params: {
            article_id: article_id,
            user_id: user_id,
        }
    })
}

export function purchaseArticle(article_id, user_id) {
    return request({
        method: 'get',
        url: '/article/purchaseArticle',
        params: {
            article_id: article_id,
            user_id: user_id,
        }
    })
}

export function downloadArticle(id) {
    return request({
        method: 'get',
        url: '/article/downloadArticle',
        params: {
            id: id,
        }
    })
}

export function storeArticle(article_id, user_id) {
    return request({
        method: 'get',
        url: '/article/storeArticle',
        params: {
            article_id: article_id,
            user_id: user_id,
        }
    })
}

export function storeCancel(article_id, user_id) {
    return request({
        method: 'get',
        url: '/article/storeCancel',
        params: {
            article_id: article_id,
            user_id: user_id,
        }
    })
}
