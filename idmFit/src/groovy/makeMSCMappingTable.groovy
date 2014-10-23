
class makeMSCMappingTable {
	static main(args) {
def edirOU = """dn: ou=WA,o=HUEBNER
dn: ou=BE,o=HUEBNER
dn: ou=USER,ou=US,o=HUEBNER
dn: ou=USER,ou=SE,o=HUEBNER
dn: ou=USER,ou=RU,o=HUEBNER
dn: ou=USER,ou=PTO,o=HUEBNER
dn: ou=USER,ou=IT,o=HUEBNER
dn: ou=USER,ou=IN,o=HUEBNER
dn: ou=USER,ou=HU,o=HUEBNER
dn: ou=USER,ou=HMC,o=HUEBNER
dn: ou=USER,ou=HIS,o=HUEBNER
dn: ou=USER,ou=HFT,o=HUEBNER
dn: ou=USER,ou=FR,o=HUEBNER
dn: ou=USER,ou=EU,o=HUEBNER
dn: ou=USER,ou=BR,o=HUEBNER
dn: ou=User,ou=ASIA,o=HUEBNER
dn: ou=USER,ou=AFRICA,o=HUEBNER
"""
edirOU.eachLine {
	println """<row>
	<col>${it.split(',')[it.split(',').size()-2].replaceAll('ou=','')}</col>
	<col>${it.replaceFirst('dn: ', '')}</col>
	<col>${it.replaceFirst('dn: ', '').replaceAll('o=HUEBNER', 'OU=User,OU=HUB,DC=2k12,DC=tsc')}</col>
	<col>mirrored</col>
</row>"""

}	
	
	
	}

}
