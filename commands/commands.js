const Discord = require('discord.js');
const mods = require('../configs/mods.js');


const any = (user, channel) => true;
const mod = (user, channel) => user.roles.cache.find(r => r.name === 'Mod Gang') != undefined;


module.exports = [{
    name: "issue",
    desc: "How to create an issue",
    permissions: any,
    runs: (user, channel, tokens) => {
      if (tokens.length == 1) {
        channel.send(new Discord.MessageEmbed().setTitle("Create an issue").setColor(0xff5500).setDescription("I won't support issues in here, so go to github, https://github.com/OroArmor/Netherite-Plus-Mod/issues, and place an issue. As well, if you dont provide a log or screenshots, I will not help you."));
      } else {
        var mod = null;
        for (let modIndex in mods) {
          if (mods[modIndex].id == tokens[1] || mods[modIndex].alias == tokens[1]) {
            mod = mods[modIndex];
            break;
          }
        }
        if (mod == null) {
          channel.send(`Mod: \`${tokens[1]}\`, is not a valid mod.`)
        } else {
          channel.send(new Discord.MessageEmbed().setTitle("Create an issue").setColor(0xff5500).setDescription(`I won't support issues in here, so go to github, ${mod.links.github}/issues, and place an issue. As well, if you dont provide a log or screenshots, I will not help you.`));
        }
      }
    }
  }
]
