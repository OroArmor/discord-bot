const Discord = require('discord.js');
const client = new Discord.Client();
const express = require('express');

const app = express()
const port = process.env.PORT || 3000;

app.get('/', (req, res) => {
  res.send('This is not a real thing!')
})

app.listen(port, () => {
})

let commandPrefix = {
  prefix: "!"
};

client.on('ready', () => {
  console.log(`Logged in as ${client.user.tag}!`);
});

let commands = ["youtube", "curseforge", "apply-for-mod"];

client.on('message', msg => {
  if (!msg.content.startsWith(commandPrefix.prefix))
    return;

  let tokens = msg.content.split(/\s+/);
  let command = tokens[0].substring(1);

  if (command === `change-prefix` && msg.member.permissions.has(Discord.Permissions.ADMINISTRATOR)) {
    msg.reply(`Changing command prefix to ${tokens[1][0]}`)
    commandPrefix.prefix = tokens[1][0];
  } else if (command === `youtube`) {
    const embed = new Discord.MessageEmbed()
      // Set the title of the field
      .setTitle('Youtube')
      // Set the color of the embed
      .setColor(0xff0000)
      // Set the main content of the embed
      .setDescription('OroArmor\'s youtube channel: https://www.youtube.com/channel/UCsbVQJhwgXIJ035XUCN9IRw');
    // Send the embed to the same channel as the message
    msg.channel.send(embed);
  } else if (command === `curseforge`) {
    const embed = new Discord.MessageEmbed()
      // Set the title of the field
      .setTitle('CurseForge')
      // Set the color of the embed
      .setColor(0x6441A4)
      // Set the main content of the embed
      .setDescription('OroArmor\'s curseforge page: https://www.curseforge.com/members/oroarmor/projects');
    // Send the embed to the same channel as the message
    msg.channel.send(embed);
  } else if (command === `commands`) {
    let message = "Available commands are:";
    for (let commandValue in commands) {
      message += `\n ${commandPrefix.prefix}${commands[commandValue]}`
    }
    msg.reply(message);
  } else if (command === `apply-for-mod`) {
    msg.reply("Apply for moderator at: https://forms.gle/F4LPDH7xD96VknY39")
  } else {
    msg.reply("Command not known");
  }
});

client.on("guildMemberAdd", member => {
  let role = member.guild.roles.cache.find(r => r.name === 'Mod Lover')
  member.roles.add(role);
})

client.login(`${process.env.BOT_TOKEN}`);
