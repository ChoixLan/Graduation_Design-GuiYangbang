import Vue from "vue";
import Vuex from "vuex";

Vue.use(Vuex);

export default new Vuex.Store({
  state: {
    token: sessionStorage.getItem("token") || "",
  },
  getters: {
  },
  mutations: {
      setToken(state, data) {
          state.token = data;
          sessionStorage.setItem("token", data);
      }
  },
  actions: {},
  modules: {},
});
