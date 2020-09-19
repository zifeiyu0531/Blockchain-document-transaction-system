import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

const store = new Vuex.Store({
	state: {
		isLogin: false
	},

	getters: {
		loginStatus: (state) => {
			return state.isLogin
		}
	},

	mutations: {
		setStatus(state, flag) {
			state.isLogin = flag
		}
	},

	actions: {
		setStatus(context, flag) {
			context.commit('setStatus', flag);
		}
	}
})

export default store
