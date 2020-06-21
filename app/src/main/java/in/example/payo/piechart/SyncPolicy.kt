package `in`.example.payo.piechart

class SyncPolicy {

        object SMSPolicy {

            private var blockedSenders = listOf<String>()
            private var blockedKeywords = listOf<String>()
            private var whitelistSenders = listOf<String>()

            val ProviderConstants  =  mapOf(
                    Pair("A", "Bharti Airtel and Bharti Hexacom"),
                    Pair("B" , "BSNL"),
                    Pair("C" , "Datacom"),
                    Pair("D" , "Aircel and Dishnet Wireless"),
                    Pair("E" , "Reliance Telecom"),
                    Pair("H" , "HFCL"),
                    Pair("I" , "Idea Cellular Aditya Birla Telecom"),
                    Pair("L" , "Loop Telecom and BPL"),
                    Pair("M" , "MTNL"),
                    Pair("P" , "Spice Communications"),
                    Pair("R" , "Reliance Communications"),
                    Pair("S" , "S. Tel Ltd"),
                    Pair("T" , "Tata Teleservices Tata Teleservices Maharashtra"),
                    Pair("U" , "Unitech Group of Companies (Telenor)"),
                    Pair("V" , "Vodafone Group of Companies"),
                    Pair("W" , "Swan Telecom"),
                    Pair("Y" , "Shyam Telecom")
            )

            val ServiceAreaConstants  =  mapOf(
                    Pair("A" , "Andhra Pradesh"),
                    Pair("B" , "Bihar"),
                    Pair("D" , "Delhi"),
                    Pair("E" , "UP (East)"),
                    Pair("G" , "Gujarat"),
                    Pair("H" , "Haryana"),
                    Pair("I" , "Himachal Pradesh"),
                    Pair("J" , "Jammu & Kashmir"),
                    Pair("K" , "Kolkata"),
                    Pair("L" , "Kerala"),
                    Pair("M" , "Mumbai"),
                    Pair("N" , "North East"),
                    Pair("O" , "Orissa"),
                    Pair("P" , "Punjab"),
                    Pair("R" , "Rajasthan"),
                    Pair("S" , "Assam"),
                    Pair("T" , "Tamil Nadu, including Chennai"),
                    Pair("V", "West Bengal"),
                    Pair("W" , "UP (West)"),
                    Pair("X" , "Karnataka"),
                    Pair("Y" , "Madhya Pradesh"),
                    Pair("Z" , "Maharashtra")
            )

            fun doesContainBlockedKeyWords(message: String) : Boolean {
                for(value in blockedKeywords) {
                    if(value != "" && message.contains(value, true)) {
                        return true
                    }
                }
                return false
            }

            fun isSenderBlocked(sender: String) : Boolean {
                if(blockedSenders.contains(sender.replace(" ", ""))) {
                    return true
                }
                return false
            }

            fun isSenderWhitelisted(sender: String) : Boolean {
                if(whitelistSenders.contains(sender.replace(" ", ""))) {
                    return true
                }
                return false
            }

        }

}