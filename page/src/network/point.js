import { request } from "./request";

export function getSalePoint(pageSize, pageNum, salePointType, salePointNumMin, salePointNumMax, salePointPriceMin, salePointPriceMax) {
    return request({
        method: 'post',
        url: '/point/searchSalePointByScreen',
        data: {
            pageSize: pageSize,
            pageNum: pageNum,
            salePointType: salePointType,
            salePointNumMin: salePointNumMin,
            salePointNumMax: salePointNumMax,
            salePointPriceMin: salePointPriceMin,
            salePointPriceMax: salePointPriceMax,
        }
    })
}

export function getRecentPrice() {
    return request({
        method: 'get',
        url: '/point/getLastWeekOrder',
        params: {}
    })
}

export function purchaseRetailPoint(salePointId, buyUserId, orderPoint) {
    return request({
        method: 'post',
        url: '/point/purchaseRetailPoint',
        data: {
            salePointId: salePointId,
            buyUserId: buyUserId,
            orderPoint: orderPoint,
        }
    })
}

export function purchasePackPoint(salePointId, buyUserId) {
    return request({
        method: 'post',
        url: '/point/purchasePackPoint',
        data: {
            salePointId: salePointId,
            buyUserId: buyUserId,
        }
    })
}

export function pointOnShelf(salePointId, salePointNum, salePointUser, salePointType, salePointRemain, salePointPrice) {
    return request({
        method: 'post',
        url: '/point/pointOnShelf',
        data: {
            salePointId: salePointId,
            salePointNum: salePointNum,
            salePointUser: salePointUser,
            salePointType: salePointType,
            salePointRemain: salePointRemain,
            salePointPrice: salePointPrice,
        }
    })
}

export function pointOffShelf(id) {
    return request({
        method: 'get',
        url: '/point/pointOffShelf',
        params: {
            id: id,
        }
    })
}
