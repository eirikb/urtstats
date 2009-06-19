class BootStrap {

    def init = { servletContext ->
        new Item(urtID:1, name:"Ka-Bar Knife").save()
        new Item(urtID:2, name:"Beretta", binding:"F").save()
        new Item(urtID:3, name:"Desert Eagle", binding:"G").save()
        new Item(urtID:4, name:"SPAS 12", binding:"H").save()
        new Item(urtID:5, name:"MP5K", binding:"I").save()
        new Item(urtID:6, name:"UMP45", binding:"J").save()
        new Item(urtID:7, name:"HK69", binding:"K").save()
        new Item(urtID:8, name:"300ML", binding:"L").save()
        new Item(urtID:9, name:"G36", binding:"M").save()
        new Item(urtID:10, name:"PSG-1", binding:"N").save()
        new Item(urtID:11, name:"High Explosive Grenade", binding:"O").save()
        
        new Item(urtID:13, name:"Smoke Grenade", binding:"Q").save()
        new Item(urtID:14, name:"SR8", binding:"Z").save()
        new Item(urtID:15, name:"AK-103", binding:"a").save()
        new Item(urtID:16, name:"Negev", binding:"c").save()

        new Item(urtID:17, name:"Kevlar Vest", binding:"R").save()
        new Item(urtID:18, name:"Kevlar Helmet", binding:"W").save()
        new Item(urtID:19, name:"Silencer", binding:"U").save()
        new Item(urtID:20, name:"Laser sight", binding:"V").save()
        new Item(urtID:21, name:"Medkit", binding:"T").save()
        new Item(urtID:22, name:"NVGs", binding:"S").save()
        new Item(urtID:23, name:"Extra Ammo", binding:"X").save()
    }



    def destroy = {
    }
} 