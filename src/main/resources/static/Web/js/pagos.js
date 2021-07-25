const app = Vue.createApp({
    data (){
        return {
            cliente:[],
            cards:[],
            pagos:[],
            

            
            
        };
    },
    created(){
            axios.get('/api/clients/current')
            .then(res=> {
             this.cliente=res.data
             this.pagos=res.data.pagos
             this.cards=res.data.cards.filter((e=>e.cardType=='CREDIT' || e.cardType==='DEBIT')).filter((e=>e.active==true))
             
            })
    },
    methods:{
        formatDate(date){
            return new Date(date).toLocaleDateString('en-GB');
        },
        
        logout(){
            axios.post('/api/logout').then(response => console.log('signed out!!!'))
            .then(response=>window.location.href = "index.html")
        },
    },
    
});

app.mount("#app"); 