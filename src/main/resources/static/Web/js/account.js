const app = Vue.createApp({
    data (){
        return {
            account:[],
            transacciones:[],
            NumAccount:"",
            
        };
    },
    created(){
            const urlParams = new URLSearchParams(window.location.search)
            const myParam = urlParams.get('id')
            axios.get('/api/accounts/'+myParam)            //http://localhost:8080/web/account.html
            .then(res=> {
             this.account=res.data   
             this.transacciones=res.data.transactions   
             
        
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
    computed:{
        orderByID(){
            var arrayAux=this.transacciones             
                    arrayAux.sort( (a,b) => {
                    if ( a.id < b.id) {
                        if(false){
                            return -1
                        }else {
                            return 1
                        }
                    }
                    if ( a.id > b.id) {
                        if(false){
                            return 1
                        }else {
                            return -1
                        }
                    }
                    return 0
                    
                    }) 
                    return arrayAux;
        },  
    },  
        
});

app.mount("#app"); 


