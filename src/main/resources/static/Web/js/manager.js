const app = Vue.createApp({
    data (){

        return {
            clientes:[], 
            nombre:"",
            apellido:"",
            email:"",
            json:"",
        } ;
    },
    created(){
        axios.get('/rest/clients')
        .then(res => {
            this.clientes=res.data._embedded.clients
            this.json=res.data._embedded.clients
                                })
    },
    methods:{
        enviarDatos(){
            axios.post('/rest/clients',{
                firstName:this.nombre,
                lastName:this.apellido,
                email:this.email
            })
        alert("Datos ingresados")
    },  
    eliminarDato(cliente){
        axios.delete(cliente._links.self.href)
        location.reload()
    },
    logout(){
        axios.post('/api/logout').then(response => console.log('signed out!!!'))
        .then(response=>window.location.href = "index.html")
    }, 
} 

});


app.mount("#app"); 