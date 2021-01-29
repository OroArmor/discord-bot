const Discord = require('discord.js');
const mods = require('../configs/mods.js');


const any = (user, channel) => true;
const mod = (user, channel) => user.roles.cache.find(r => r.name === 'Mod Gang') != undefined;


module.exports = [{
    name: "youtube",
    desc: "Links to OroArmor's youtube channel",
    permissions: any,
    runs: (user, channel, tokens) => {
      channel.send(new Discord.MessageEmbed().setTitle('Youtube').setColor(0xff0000).setDescription('OroArmor\'s youtube channel: https://www.youtube.com/channel/UCsbVQJhwgXIJ035XUCN9IRw'));
    }
  }, {
    name: "curseforge",
    desc: "Links to OroArmor's curseforge page",
    permissions: any,
    runs: (user, channel, tokens) => {
      channel.send(new Discord.MessageEmbed().setTitle('CurseForge').setColor(0x6441A4).setDescription('OroArmor\'s curseforge page: https://www.curseforge.com/members/oroarmor/projects'));
    }
  }, {
    name: "modrinth",
    desc: "Links to OroArmor's modrinth page",
    permissions: any,
    runs: (user, channel, tokens) => {
      channel.send(new Discord.MessageEmbed().setTitle('Modrinth').setColor(0x079515).setDescription('OroArmor\'s modrinth page: https://modrinth.com/user/h74rYEcI'));
    }
  }, {
    name: "github",
    desc: "Links to OroArmor's Github page",
    permissions: any,
    runs: (user, channel, tokens) => {
      channel.send(new Discord.MessageEmbed().setTitle('Github').setColor(0x555555).setDescription('OroArmor\'s Github page: https://github.com/OroArmor'));
    }
  }, {
    name: "bintray",
    desc: "Links to OroArmor's Bintray repository",
    permissions: any,
    runs: (user, channel, tokens) => {
      channel.send(new Discord.MessageEmbed().setTitle('Github').setColor(0x079515).setDescription('OroArmor\'s Bintray page: https://bintray.com/oroarmor/oroarmor\nOroArmor\'s Bintray Repo: https://dl.bintray.com/oroarmor/oroarmor/'));
    }
  }, {
    name: "apply-for-mod",
    desc: "Provides the link to the moderator application page",
    permissions: any,
    runs: (user, channel, tokens) => {
      channel.send("Apply for moderator at: https://forms.gle/F4LPDH7xD96VknY39");
    }
  }, {
    name: "patreon",
    desc: "Support me on Patreon",
    permissions: any,
    runs: (user, channel, tokens) => {
      channel.send("https://patreon.com/oroarmor");
    }
  }, {
    name: "architectury",
    desc: "Links to architectury",
    permissions: any,
    runs: (user, channel, tokens) => {
      channel.send(new Discord.MessageEmbed().setTitle('Architectury').setColor(0xc76003).setDescription('Architectury is required to run Netherite Plus. Download the fabric version: https://www.curseforge.com/minecraft/mc-mods/architectury-fabric or the forge version: https://www.curseforge.com/minecraft/mc-mods/architectury-forge.'));
    }
  }, {
    name: "mod",
    desc: "Gets the info about a mod",
    permissions: any,
    runs: (user, channel, tokens) => {
      if (tokens.length == 1) {
        let message = "No mod provided! Available mods are:```"
        for (let mod in mods) {
          message += `\n${mods[mod].id}: ${mods[mod].alias}`
        }
        channel.send(message + "```")
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
          channel.send(new Discord.MessageEmbed().setTitle(`${mod.name}`).setDescription(`${mod.extdesc}\n\nFind on Github: ${mod.links.github}\n${(mod.links.curseforge)? "Find on CurseForge: "+ mod.links.curseforge : ""}\n${(mod.links.modrinth)? "Find on Modrinth: "+ mod.links.modrinth : ""}`));
        }
      }
    }
  }, {
    name: "mods",
    desc: "Gets the info about mods",
    permissions: any,
    runs: (user, channel, tokens) => {
      if (tokens.length == 1) {
        let message = "My mods are:"
        for (let mod in mods) {
          message += `\n**${mods[mod].name} (${mods[mod].alias})**: ${mods[mod].desc}`
        }
        channel.send(new Discord.MessageEmbed().setTitle(`My Mods`).setDescription(message))
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
          channel.send(new Discord.MessageEmbed().setTitle(`${mod.name}`).setDescription(`${mod.extdesc}\n\nFind on Github: ${mod.links.github}\n${(mod.links.curseforge)? "Find on CurseForge: "+ mod.links.curseforge : ""}\n${(mod.links.modrinth)? "Find on Modrinth: "+ mod.links.modrinth : ""}`));
        }
      }
    }
  }, {
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
  },
  //dud commands (dont run from here as they are special)
  {
    name: "commands",
    desc: "List all commands available to the user",
    permissions: any
  },
  {
    name: "change-prefix",
    desc: "Changes the command prefix (Only for moderators)",
    permissions: mod
  }
]
