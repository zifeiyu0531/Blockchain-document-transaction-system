import {request} from "./request";

export function userLogin(userName, userPassword) {
  return request({
    method: 'post',
    url: '/user/signIn',
    data: {
      userName: userName,
      userPassword: userPassword,
    }
  })
}

export function userRegister(userName, userPassword, userPhone) {
  return request({
    method: 'post',
    url: '/user/signUp',
    data: {
      userName: userName,
      userPassword: userPassword,
	  userPhone: userPhone,
    }
  })
}

export function getSMSCode(userPhone) {
  return request({
    method: 'get',
    url: '/user/sendMessage',
    params: {
      userPhone: userPhone
    }
  })
}

export function userRemain(userId) {
  return request({
    method: 'get',
    url: '/user/getRemain',
    params: {
      id: userId
    }
  })
}

export function userIncome(userId) {
  return request({
    method: 'get',
    url: '/user/getIncome',
    params: {
      id: userId
    }
  })
}

export function userBankCard(userId) {
  return request({
    method: 'get',
    url: '/user/getBankAccount',
    params: {
      id: userId
    }
  })
}

export function bindBankCard(userId, bankAccount, bankId, phoneNumber, idCardNumber, realName) {
  return request({
    method: 'post',
    url: '/user/bindBankAccount',
    data: {
      userId: userId,
      bankAccount: bankAccount,
	  bankId: bankId,
	  phoneNumber: phoneNumber,
	  idCardNumber: idCardNumber,
	  realName: realName
    }
  })
}

export function userWithdraw(userId, userRmb, bankAccount) {
  return request({
    method: 'post',
    url: '/user/getCash',
    data: {
      id: userId,
      cash: userRmb,
	  bankAccount: bankAccount,
    }
  })
}

export function userRmbHistory(userId) {
  return request({
    method: 'get',
    url: '/user/getRmbHistory',
    params: {
      id: userId
    }
  })
}

export function userPointHistory(userId) {
  return request({
    method: 'get',
    url: '/user/getPointHistory',
    params: {
      id: userId
    }
  })
}


export function getUploadArticle(userId) {
  return request({
    method: 'get',
    url: '/user/getUploadArticle',
    params: {
      id: userId
    }
  })
}

export function getPurchaseArticle(userId) {
  return request({
    method: 'get',
    url: '/user/getPurchaseArticle',
    params: {
      id: userId
    }
  })
}

export function getStoreArticle(userId) {
  return request({
    method: 'get',
    url: '/user/getStoreArticle',
    params: {
      id: userId
    }
  })
}

export function getSalePoint(userId) {
  return request({
    method: 'get',
    url: '/user/getSalePoint',
    params: {
      id: userId
    }
  })
}